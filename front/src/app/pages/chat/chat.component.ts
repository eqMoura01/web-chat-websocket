import { CommonModule } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup, ReactiveFormsModule } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { ChatService } from '../../services/chat/chat.service';

interface Mensagem {
  chat: {
    id: number
  };
  remetente: {
    id: number,
    username: string
  };
  destinatario: {
    id: number,
    username: string
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
  usuarioLogado: any

  constructor(private route: ActivatedRoute, private chatService: ChatService) {
    this.chatForm = new FormGroup({
      message: new FormControl(''),
    });

  }

  ngOnInit(): void {
    this.getMessages(1);
    this.usuarioLogado = history.state.usuarioLogado;
    this.userId = this.usuarioLogado.id;
  }

  sendMessage(): void {
    const messageContent = this.chatForm.get('message')?.value;
    console.log('Mensagem enviada com sucesso! ' + JSON.parse(localStorage.getItem('usuario-logado')!).username + " " + messageContent);

    if (messageContent) {

      this.chatService.sendMessage(1, this.usuarioLogado.id, history.state.destinatario, messageContent);

      this.chatForm.reset();
    }
  }

  async getMessages(chatId: number): Promise<void> {
    const response = await fetch(`http://localhost:8080/mensagem/chat/${chatId}`);
    const data = await response.json();
    this.mensagens = data;
    console.log('Usuario logado', this.usuarioLogado);
    console.log('Mensagens:', data);
  }

  handleBackPage(): void {
    window.history.back();
  }
}
