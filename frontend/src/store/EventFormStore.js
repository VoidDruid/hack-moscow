import {action, computed, observable} from "mobx";

class EventFormStore{
    @observable title;
    @observable description;
    @observable date;//YYYY:MM:DD
    @observable _loc;

    @action
    setLoc(latLong) {
        this._loc = {lat: latLong[0], long: latLong[1]}
    }
    @computed get loc() {
        return this._loc
    }
    @action submit() {

    }
}

export const eventFormStore = new EventFormStore();
