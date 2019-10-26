import { observable, action, computed } from "mobx";

class Historystore {
    @observable _isAuth = false || localStorage.getItem('user') === 'true';

    @action setAuth(){
        localStorage.setItem('user', 'true')
        this._isAuth = true
    }
    @computed get isAuth() {
        return this._isAuth
    }
    @action logOut(){
        localStorage.removeItem('user')
        this._isAuth = false
    }

}
export const historyStore = new Historystore()
