import { Component, OnInit, OnDestroy } from '@angular/core';
import { FormControl, FormGroup, ReactiveFormsModule } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { ChatService } from '../../services/chat/chat.service';
import { Mensagem } from '../../interfaces/mensagem';
import { Subscription } from 'rxjs';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-chat',
  templateUrl: './chat.component.html',
  styleUrls: ['./chat.component.scss'],
  standalone: true,
  imports: [ReactiveFormsModule, CommonModule],
})
export class ChatComponent implements OnInit, OnDestroy {
  userId!: number;
  chatId!: number;
  mensagens: Mensagem[] = [];
  chatForm: FormGroup;
  usuarioLogado: any;
  private mensagensSubscription!: Subscription;

  constructor(private route: ActivatedRoute, private chatService: ChatService) {
    this.chatForm = new FormGroup({
      message: new FormControl(''),
    });
  }

  ngOnInit(): void {
    this.usuarioLogado = history.state.usuarioLogado;
    this.userId = this.usuarioLogado.id;
    this.getChatByUsersId(this.usuarioLogado.id, history.state.destinatario);

    this.initializeChat();
  }

  initializeChat(): void {
    // Assinatura reativa das mensagens recebidas
    this.mensagensSubscription = this.chatService.getMessages().subscribe((mensagem: Mensagem) => {
      this.mensagens.push(mensagem);
    });
  }

  sendMessage(): void {
    const messageContent = this.chatForm.get('message')?.value;

    if (messageContent) {
      const mensagem: Mensagem = {
        chat: {
          id: this.chatId,
        },
        remetente: {
          id: this.usuarioLogado.id,
          username: this.usuarioLogado.username,
        },
        destinatario: {
          id: history.state.destinatario,
          username: '',
        },
        conteudo: messageContent,
      };
      this.chatService.sendMessage(mensagem);
      this.mensagens.push(mensagem)

      this.chatForm.reset();
    }
  }

  async getMessages(chatId: number): Promise<void> {
    const response = await fetch(`http://localhost:8080/mensagem/chat/${chatId}`);
    console.log(chatId);
    const data = await response.json();
    this.mensagens = data;
  }

  async getChatByUsersId(idRemetente: number, idDestinatario: number): Promise<void> {
    const response = await fetch(`http://localhost:8080/chat/userId1/${idRemetente}/userId2/${idDestinatario}`);

    console.log('idRemetente: ' + idRemetente + ', idDestinatario: ' + idDestinatario)

    const data = await response.json();
    if (data.id) {
      this.chatId = data.id;
      this.getMessages(data.id);
    }
    return data.id;
  }

  handleBackPage(): void {
    window.history.back();
  }

  ngOnDestroy(): void {
    if (this.mensagensSubscription) {
      this.mensagensSubscription.unsubscribe();
    }
  }
}
