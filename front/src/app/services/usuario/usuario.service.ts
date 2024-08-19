import { Injectable } from '@angular/core';
import axios from 'axios';

@Injectable({
  providedIn: 'root'
})
export class UserService {

  private apiUrl = 'http://localhost:8080/usuario'; // URL do seu backend

  constructor() { }

  // Método para obter a lista de usuários
  async getUsers(): Promise<User[]> {
    try {
      const response = await axios.get<User[]>(this.apiUrl);
      return response.data;
    } catch (error) {
      console.error('Erro ao obter usuários:', error);
      throw error; // Opcional: você pode querer tratar o erro de forma diferente
    }
  }
}

// Interface para representar um usuário
export interface User {
  id: number;
  username: string;
}
