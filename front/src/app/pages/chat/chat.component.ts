import { CommonModule } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup, ReactiveFormsModule } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { ChatService } from '../../services/chat/chat.service';

interface Mensagem {
  chat: {
    id: number;
  };
  usuario: {
    id: number;
    username?: string;
  };
  conteudo: string;
}

@Component({
  selector: 'app-chat',
  templateUrl: './chat.component.html',
  styleUrls: ['./chat.component.scss'],
  standalone: true,
  imports: [ReactiveFormsModule, CommonModule],
})
export class ChatComponent implements OnInit {
  userId!: number;
  mensagens: Mensagem[] = [];
  chatForm: FormGroup;
  usuarioLogado: { id: number; username: string } = JSON.parse(localStorage.getItem('usuario-logado')!);

  constructor(private route: ActivatedRoute, private chatService: ChatService) {
    this.chatForm = new FormGroup({
      message: new FormControl(''),
    });

  }

  ngOnInit(): void {
    this.userId = this.usuarioLogado.id;
    this.initializeChat();
    this.getMessages(3);

  }

  initializeChat(): void {
    // Assinatura das mensagens recebidas em tempo real via WebSocket
    this.chatService.getMessages().subscribe((mensagem: Mensagem) => {
      this.mensagens.push({
        chat: {
          id: mensagem.chat.id,
        },
        usuario: {
          id: mensagem.usuario.id,
          username: mensagem.usuario.username,
        },
        conteudo: mensagem.conteudo,
      });
      console.log('Nova mensagem recebida:', mensagem);
    });
  }

  sendMessage(): void {
    const messageContent = this.chatForm.get('message')?.value;
    console.log('Mensagem enviada com sucesso!' + JSON.parse(localStorage.getItem('usuario-logado')!).username + messageContent);

    if (messageContent) {
      this.chatService.sendMessage(2, messageContent); // Substitua o ID do chat conforme necessário
      this.chatForm.reset();
    }
  }

  async getMessages(chatId: number): Promise<void> {
    const response = await fetch(`http://localhost:8080/mensagem/chat/${chatId}`);
    const data = await response.json();
    this.mensagens = data;
  }

  handleBackPage(): void {
    window.history.back();
  }
}
