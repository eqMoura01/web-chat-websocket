import { Component } from '@angular/core';
import { FormControl, FormGroup, FormsModule, ReactiveFormsModule } from '@angular/forms';
import { Router, RouterLink } from '@angular/router';
import axios from 'axios';

@Component({
  selector: 'app-login',
  standalone: true,
  imports: [FormsModule, ReactiveFormsModule, RouterLink],
  templateUrl: './login.component.html',
  styleUrl: './login.component.scss'
})
export class LoginComponent {

  constructor(private router: Router) {

  }

  form = new FormGroup({
    username: new FormControl(''),
    password: new FormControl('')
  });

  handleLogin() {
    console.log({
      username: this.form.value.password,
      password: this.form.value.password
    });

    axios.post('http://localhost:8080/usuario/login', {
      username: this.form.value.username,
      password: this.form.value.password
    }).then((response) => {

      localStorage.setItem('usuario-logado', JSON.stringify({
        id: response.data.id,
        username: response.data.username
      }));

      this.router.navigate(['/home']);
    }).catch((error) => {
      console.log(error);
    }
    )
  }

}
