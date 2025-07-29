import { Component } from '@angular/core';
import { RouterModule } from '@angular/router';

@Component({
  selector: 'app-meu-perfil',
  imports: [RouterModule],
  templateUrl: './meu-perfil.html',
  styleUrl: './meu-perfil.scss'
})
export class MeuPerfil {
  taEditando = false;

  alteraEditar(){
    this.taEditando = !this.taEditando;
    console.log(this.taEditando)
  }
}
