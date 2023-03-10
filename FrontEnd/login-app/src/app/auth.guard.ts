import { Injectable } from '@angular/core';
import { CanActivate, Router } from '@angular/router';

import { AuthService } from '../app/auth.service';

@Injectable({
  providedIn: 'root'
})
export class AuthGuard implements CanActivate {

  constructor(private authService: AuthService, private router: Router) {}

  canActivate(): boolean {
    const currentUser = this.authService.getCurrentUser();

    if (currentUser) {
      // user is logged in
      return true;
    }

    // user is not logged in, redirect to login page
    this.router.navigate(['/login']);
    return false;
  }

}
