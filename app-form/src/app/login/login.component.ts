import { Component } from '@angular/core';
import { UserService } from '../user.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent {
  username: string = '';
  password: string = '';

  constructor(private userService: UserService, private router: Router) { }

  onSubmit() {
    this.userService.authenticateUser(this.username, this.password).subscribe(
      response => {
        console.log(response);
        if (response.message === 'Login exitoso') {
          const userId = response.userId; // ID de usuario desde la respuesta del servicio
          this.router.navigateByUrl(`/facturascompensar?userId=${userId}`);
          console.log('Entra');
        } else {
          console.log('Error de autenticación');
        }
      },
      error => {
        console.log('Ocurrió un error durante la autenticación', error);
      }
    );
  }
}

// export class LoginComponent {
//   username: string='';
//   password: string='';

//   constructor(private userService: UserService, private router: Router) { }

//   onSubmit() {
//     this.userService.authenticateUser(this.username, this.password).subscribe(
//       response => {
//         console.log(response);
//         if (response.message === 'Login exitoso') {
//           this.router.navigateByUrl('/formulario');
//           console.log('Entra');
//         } else {
//           console.log('Error de autenticación');
//         }
//       },
//       error => {
//         console.log('Ocurrió un error durante la autenticación', error);
//       }
//     );
//   }

// }

