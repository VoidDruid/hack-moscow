import React, {Component} from 'react';
import {observer} from 'mobx-react';
import {observable} from 'mobx';
import Auth from '../../../Auth';


@observer class SignIn extends Component{
    @observable password = '';
    @observable login = '';

    handleClick = () =>{
        alert(this.password)
        Auth.login(()=>{})
    }

    render(){
        return(
            <form onSubmit={(e) => e.preventDefault()}>
                <div>
                    <input type="text" value={this.login} placeholder="example@here.com" onChange={(e)=>{this.login = e.target.value}}></input>
                </div>
                <div>
                    <input type="password" value={this.password} placeholder="12345678" onChange={(e)=>{this.password = e.target.value}}></input>
                </div>
                <button onClick={this.handleClick}>
                    Log in
                </button>
            </form>
        );
    }
}

export default SignIn;

