import { Injectable } from '@angular/core';
import { Observable, Subject } from 'rxjs';
import { Client } from '@stomp/stompjs';

export interface Mensagem {
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



@Injectable({
  providedIn: 'root'
})
export class ChatService {
  private stompClient: Client;
  private messagesSubject = new Subject<Mensagem>();
  private usuarioLogado = JSON.parse(localStorage.getItem('usuario-logado')!);


  constructor() {
    this.stompClient = new Client({
      brokerURL: 'ws://localhost:8080/websocket',
      reconnectDelay: 5000,
      debug: (str) => {
        console.warn(`debug: ${str}`)
      }
    });

    this.stompClient.activate();

    this.stompClient.onConnect = () => {
      console.log('Conectado com sucesso!');
      // Assinando o tópico específico do chat
      this.stompClient.subscribe(`/topic/user/${this.usuarioLogado.id}`, (message) => {
        this.messagesSubject.next(JSON.parse(message.body));
        console.log('Nova mensagem recebida:', JSON.parse(message.body));
      });
    };

    this.stompClient.onStompError = (frame) => {
      console.error('Erro STOMP:', frame);
    };

    this.stompClient.onWebSocketError = (event) => {
      console.error('Erro WebSocket:', event);
    };
  }

  sendMessage(chatId: number, idRemetente: number, idDestinatario: number, messageContent: string): void {
    const mensagem: Mensagem = {
      chat: {
        id: chatId
      },
      remetente: {
        id: idRemetente,
        username: ''
      },
      destinatario: {
        id: idDestinatario,
        username: ''
      },
      conteudo: messageContent,
    };

    console.log('Mensagem:', mensagem);

    this.stompClient.publish({
      destination: '/app/chat.send',
      body: JSON.stringify(mensagem)
    });
  }

  getMessages(): Observable<Mensagem> {
    console.log('Mensagens:', this.messagesSubject.asObservable());
    return this.messagesSubject.asObservable();
  }
}
