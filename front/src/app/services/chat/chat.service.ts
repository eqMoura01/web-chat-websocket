import { Injectable } from '@angular/core';
import { Observable, Subject } from 'rxjs';
import { Client, Stomp } from '@stomp/stompjs';

interface Mensagem {
  chat: { id: number };
  usuario: { username: string };
  conteudo: string;
  dataEnvio: Date;
}

@Injectable({
  providedIn: 'root'
})
export class ChatService {
  private stompClient: Client;
  private messagesSubject = new Subject<Mensagem>();

  constructor() {
    this.stompClient = new Client({
      brokerURL: 'ws://localhost:8080/websocket',
      connectHeaders: {
      },
      reconnectDelay: 5000,
      debug: (str) => {
        console.warn(`debug: ${str}`) 
      }
    });

    this.stompClient.activate();

    this.stompClient.onConnect = () => {
      console.log('Conectado com sucesso!');
      this.stompClient.subscribe('/topic/', (message) => {
        this.messagesSubject.next(JSON.parse(message.body));
      });
    };

    this.stompClient.onStompError = (frame) => {
      console.error('Erro STOMP:', frame);
    };

    this.stompClient.onWebSocketError = (event) => {
      console.error('Erro WebSocket:', event);
    };
  }

  sendMessage(chatId: number, messageContent: string): void {
    const mensagem: Mensagem = {
      chat: { id: chatId },
      usuario: { username: 'username' },
      conteudo: messageContent,
      dataEnvio: new Date()
    };

    this.stompClient.publish({
      destination: '/app/chat.send',
      body: JSON.stringify(mensagem)
    });
  }

  getMessages(): Observable<Mensagem> {
    return this.messagesSubject.asObservable();
  }
}
