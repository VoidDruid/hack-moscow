import { observable, action, computed } from "mobx";

class ProfileStore {
    @observable _address = '';
    @observable _orgType = '';
    @observable _fullname = '';

    @action setData(data){
        this._address = data.address;
        this._orgType = data.orgType;
        this._fullname = data.fullname;
    }
    @computed get getData() {
        return [this._address,this._orgType,this._fullname]
    }

}
export const historyStore = new ProfileStore()
