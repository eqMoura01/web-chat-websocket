import { Injectable } from '@angular/core';
import { Observable, Subject } from 'rxjs';
import { Client } from '@stomp/stompjs';
import { Mensagem } from '../../interfaces/mensagem'; // Importe a interface `Mensagem` corretamente

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
        console.warn(`debug: ${str}`);
      }
    });

    this.stompClient.activate();

    this.stompClient.onConnect = () => {
      console.log('Conectado com sucesso!');
      // Assinando o tópico específico do chat
      this.stompClient.subscribe(`/topic/usuario/${this.usuarioLogado.id}`, (message) => {
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

  sendMessage(mensagem: Mensagem): void {
    // const mensagem: Mensagem = {
    //   chat: {
    //     id: chatId
    //   },
    //   remetente: {
    //     id: idRemetente,
    //     username: this.usuarioLogado.username
    //   },
    //   destinatario: {
    //     id: idDestinatario,
    //     username: '' // Atualize este campo conforme necessário
    //   },
    //   conteudo: messageContent,
    // };
    mensagem

    console.log('Mensagem:', mensagem);

    this.stompClient.publish({
      destination: '/app/chat.send',
      body: JSON.stringify(mensagem)
    });
  }

  getMessages(): Observable<Mensagem> {
    return this.messagesSubject.asObservable();
  }

  async getChatByUsersId(idRemetente: number, idDestinatario: number): Promise<any> {
    const response = await fetch(`http://localhost:8080/api/chat/userId1/${idRemetente}/userId2/${idDestinatario}`);
    const data = await response.json();
    console.log('Chat:', data);
    return data;
  }
}
