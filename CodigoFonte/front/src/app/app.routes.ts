import { Routes } from '@angular/router';
import { Login } from './pages/login/login';
import { Registro } from './pages/registro/registro';
import { Home } from './pages/home/home';
import { MeuPerfil } from './pages/meu-perfil/meu-perfil';
import { AlterarSenha } from './pages/alterar-senha/alterar-senha';

export const routes: Routes = [
    {
        path: "",
        redirectTo: 'login',
        pathMatch: 'full'
    },
    {
        path: "login",
        component: Login
    },
    {
        path: "registro",
        component: Registro
    },
    {
        path: "home",
        component: Home
    },
    {
        path: "meu-perfil",
        component: MeuPerfil
    },
    {
        path:"alterar-senha",
        component: AlterarSenha
    }
];
