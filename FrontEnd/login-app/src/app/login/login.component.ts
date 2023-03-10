import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { User } from '../User/user.model';
import { AuthService } from '../auth.service';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})

export class LoginComponent {

  user: User = {
    email: '',
    password: ''
  };

  constructor(private authService: AuthService, private router: Router) {}
  

  onSubmit(): void {
    this.authService.login(this.user.email, this.user.password)
      .subscribe(
        () => {
          // login successful, navigate to home page
          this.router.navigate(['/home']);
        },
        error => {
          // login failed, display error message
         console.log("Error");
         
        });
  }

}
