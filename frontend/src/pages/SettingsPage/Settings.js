import React, {Component} from 'react';
import {observer} from 'mobx-react'
import {observable} from 'mobx'
import {ProfileStore} from '../../store/ProfileStore'

@observer
class Settings extends Component{
    @observable address = '';
    @observable orgType = '';
    @observable fullname = '';
    
    _address = "Dmitroevsky highway, 9";
    _orgType = "Hack"
    _fullname = "Russian Hackers"
    handleClick = () => {
        if (this.address!==''){
            this._address = this.address
        }
        if (this.orgType!==''){
            this._orgType = this.orgType
        }
        if (this.fullname!==''){
            this._fullname = this.fullname
        }
        this.address = ''
        this.orgType = ''
        this.fullname = ''
    }
    
    

    render(){
        return(

            <form className="main-page-box" onSubmit={(e) => e.preventDefault()}>
            <header>User settings</header>
            <main>
                <section className="main-page-section">
                    <div className="main-page-header">Organization name</div>
                    <input className="main-page-description" style={{height:'30px', width:'10vw',border:'none',borderRadius:'10px',textAlign:'center'}} type="text" value={this.fullname} placeholder="Input to change" onChange={(e)=>{this.fullname = e.target.value}}></input>
                    <div className="main-page-description">{this._fullname}</div>
                </section>
                <section className="main-page-section">
                    <div className="main-page-header">Type of organization</div>
                    <input className="main-page-description" style={{height:'30px', width:'10vw',border:'none',borderRadius:'10px',textAlign:'center'}} type="text" value={this.orgType} placeholder="Input to change" onChange={(e)=>{this.orgType = e.target.value}}></input>
                    <div className="main-page-description">{this._orgType}</div>
                </section>
                <section className="main-page-section">
                    <div className="main-page-header">Legal Address</div>
                    <input className="main-page-description" style={{height:'30px', width:'10vw',border:'none',borderRadius:'10px',textAlign:'center'}} type="text" value={this.address} placeholder="Input to change" onChange={(e)=>{this.address = e.target.value}}></input>
                    <div className="main-page-description">{this._address}</div>
                </section>
                <section style={{height:'90px', width:'30vw',fontSize:'1.4em'}}>
                    <button className="form-button" onClick={this.handleClick}>Send data</button>
                </section>
            </main>

        </form>
        );
    }
}

export default Settings;