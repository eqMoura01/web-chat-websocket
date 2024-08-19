import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { ChatService } from '../../services/chat/chat.service'; // Serviço para gerenciamento de chat
import { FormControl, FormGroup, ReactiveFormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-chat',
  templateUrl: './chat.component.html',
  styleUrls: ['./chat.component.scss'],
  standalone: true,
  imports: [ReactiveFormsModule, CommonModule]
})
export class ChatComponent implements OnInit {

  userId!: number;
  messages: any[] = [];
  chatForm: FormGroup;

  constructor(private route: ActivatedRoute, private chatService: ChatService) {
    this.chatForm = new FormGroup({
      message: new FormControl('')
    });
  }

  ngOnInit(): void {
    this.userId = +this.route.snapshot.paramMap.get('id')!;
    this.initializeChat();
  }

  initializeChat(): void {
    // Assinatura das mensagens recebidas
    this.chatService.getMessages().subscribe((message: any) => {
      this.messages.push(message);
    });

    // Exemplo: Enviar uma mensagem de registro se necessário
    // this.chatService.registerUser(this.userId);
  }

  sendMessage(): void {
    const messageContent = this.chatForm.get('message')?.value;

    console.log(messageContent);

    if (messageContent) {
      this.chatService.sendMessage(this.userId, messageContent);
      this.chatForm.reset();
    }
  }
}
