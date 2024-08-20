import { Injectable } from '@angular/core';
import { Observable, Subject } from 'rxjs';
import { Client } from '@stomp/stompjs';

interface Mensagem {
  chat: { id: number };
  usuario: { id: number };
  conteudo: string;
}

@Injectable({
  providedIn: 'root'
})
export class ChatService {
  private stompClient: Client;
  private messagesSubject = new Subject<Mensagem>();
  private usuarioLogado = localStorage.getItem('usuario-logado');

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
      this.stompClient.subscribe('/topic/chat/2', (message) => {
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
      usuario: { id: JSON.parse(this.usuarioLogado!).id },
      conteudo: messageContent
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
