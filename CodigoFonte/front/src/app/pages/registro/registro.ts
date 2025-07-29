import { Component } from '@angular/core';
import { Router, RouterModule } from '@angular/router';

@Component({
  selector: 'app-registro',
  imports: [RouterModule],
  templateUrl: './registro.html',
  styleUrl: './registro.scss'
})
export class Registro {
  constructor(private router: Router) {}

  registrar() {
    // Aqui você faria a lógica de validação, envio do formulário, etc.
    // Suponha que o cadastro foi bem-sucedido:

    this.router.navigate(['/home']); // redireciona para /home
  }
}
