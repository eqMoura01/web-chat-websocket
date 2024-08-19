import { Routes } from '@angular/router';

export const routes: Routes = [
    {
        path: '',
        loadComponent() {
            return import('./pages/login/login.component').then(m => m.LoginComponent);
        }
    },
    {
        path: 'home',
        loadComponent() {
            return import('./pages/home/home.component').then(m => m.HomeComponent);
        }
    },
    {
        path: 'chat/:id',
        loadComponent() {
            console.log('Carregando o componente de chat');
            return import('./pages/chat/chat.component').then(m => m.ChatComponent);
        }
    }
];
