import { CommonModule } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router'; // Import the Router module
import { ChatService } from '../../services/chat/chat.service';
import { User, UserService } from '../../services/usuario/usuario.service';

@Component({
  selector: 'app-user-list',
  templateUrl: './home.component.html',
  standalone: true,
  styleUrls: ['./home.component.scss'],
  imports: [CommonModule]
})
export class HomeComponent implements OnInit {

  users: User[] = [];
  usuarioLogado: {
    id: number; username: string
  } = JSON.parse(localStorage.getItem('usuario-logado')!);

  constructor(private userService: UserService, private router: Router, private chatService: ChatService) { } // Inject the Router module

  ngOnInit(): void {
    this.userService.getUsers().then((users: User[]) => {
      this.users = users;

      console.log('Usuários:', users);
    });
  }

  startChat(id: number): void {
    console.log('Iniciar chat com o usuário de ID:', id);
    this.router.navigate(['/chat', id], {
      state: {
        usuarioLogado: this.usuarioLogado,
        destinatario: id
      }
    });
  }
}
