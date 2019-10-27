import React, {Component} from 'react';
import {observer} from 'mobx-react';
import {observable} from 'mobx';
import Auth from '../../../Auth';
import {withRouter} from 'react-router-dom'
import { historyStore } from '../../../../store/HistoryStore';

@observer class SignIn extends Component{
    @observable password = '';
    @observable login = '';

    handleClick = () => {
        // Auth.login(()=>{this.props.history.push("/");
        historyStore.setAuth()
    }
    
    

    render(){
        return(
            <form className="form-box" onSubmit={(e) => e.preventDefault()}>
                <p>Sign in with underlane fields</p>
                <div className="form-field">
                    <input type="text" value={this.login} placeholder="example@here.com" onChange={(e)=>{this.login = e.target.value}}></input>
                </div>
                <div className="form-field">
                    <input type="password" value={this.password} placeholder="********" onChange={(e)=>{this.password = e.target.value}}></input>
                </div>
                <button className="form-button" onClick={this.handleClick}>
                    Log in
                </button>
            </form>
        );
    }
}

export default withRouter(SignIn);

