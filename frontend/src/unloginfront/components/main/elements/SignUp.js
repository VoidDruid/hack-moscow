import React, {Component} from 'react';

class SignUp extends Component{
    render(){
        return(
            <form className="form-box-up">
                <p>Sign up and join to us!</p>
                <div className="form-field">
                    <input type="text" value={this.login} placeholder="example@here.com" onChange={(e)=>{this.login = e.target.value}}></input>
                </div>
                <div className="form-field">
                    <input type="password" value={this.password} placeholder="*********" onChange={(e)=>{this.password = e.target.value}}></input>
                </div>
                <div className="form-field">
                    <input type="text" value={this.password} placeholder="Organization Name" onChange={(e)=>{this.password = e.target.value}}></input>
                </div>
                <button className="form-button" onClick={this.handleClick}>
                    Join us
                </button>
            </form>
        );
    }
}

export default SignUp;