class Auth {
  constructor() {
    this.authenticated = Boolean(localStorage.getItem("user")) || false;
  }

  login(cb) {
    this.authenticated = true;
    cb();
    localStorage.setItem("user", "true");
  }

  logout(cb) {
    this.authenticated = false;
    cb();
    localStorage.removeItem("user");
  }

  isAuthenticated() {
    return this.authenticated;
  }
}

export default new Auth();
