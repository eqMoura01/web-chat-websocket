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
  chatId!: number;
  mensagens: Mensagem[] = [];
  chatForm: FormGroup;
  usuarioLogado: any


  constructor(private route: ActivatedRoute, private chatService: ChatService) {
    this.chatForm = new FormGroup({
      message: new FormControl(''),
    });

  }

  ngOnInit(): void {
    this.usuarioLogado = history.state.usuarioLogado;
    this.userId = this.usuarioLogado.id;
    this.getChatByUsersId(this.usuarioLogado.id, history.state.destinatario);
  }

  sendMessage(): void {
    const messageContent = this.chatForm.get('message')?.value;

    if (messageContent) {

      this.chatService.sendMessage(this.chatId, this.usuarioLogado.id, history.state.destinatario, messageContent);

      this.chatForm.reset();
    }

    this.mensagens.push({
      chat: {
        id: this.chatId
      },
      remetente: {
        id: this.usuarioLogado.id,
        username: this.usuarioLogado.username
      },
      destinatario: {
        id: history.state.destinatario,
        username: ''
      },
      conteudo: messageContent
    })
  }

  async getMessages(chatId: number): Promise<void> {
    const response = await fetch(`http://localhost:8080/mensagem/chat/${chatId}`);
    const data = await response.json();
    this.mensagens = data;
    console.log('Mensagens:', data);
  }

  async getChatByUsersId(idRemetente: number, idDestinatario: number): Promise<void> {
    const response = await fetch(`http://localhost:8080/chat/userId1/${idRemetente}/userId2/${idDestinatario}`);
    const data = await response.json();
    console.log('Chat:', data);
    if (data.id) {
      this.chatId = data.id;
      this.getMessages(data.id);
    }
    return data.id;
  }

  handleBackPage(): void {
    window.history.back();
  }
}
