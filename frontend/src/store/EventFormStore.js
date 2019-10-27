import { action, computed, observable } from "mobx";
import { rest } from "./RestStore";
class EventFormStore {
  @observable _title;
  @observable _description;
  @observable _date; //YYYY:MM:DD
  @observable _loc;

  @computed get title() {
    return this._title;
  }
  @computed get description() {
    return this._description;
  }
  @computed get date() {
    return this._date;
  }
  @computed get loc() {
    return this._loc;
  }
  @action
  setLoc(latLong) {
    this._loc = { lat: +latLong.lat, long: +latLong.lng };
  }
  @action
  setTitle(title) {
    this._title = title;
  }
  @action
  setDate(date) {
    this._date = date;
  }
  @action
  setDescription(description) {
    this._description = description;
  }
  @action submit = async () => {
    console.log(this);
    debugger;
    const { title, description, _loc } = this;
    const resp = await rest.addNewEvent({
      title,
      description,
      lat: _loc.lat,
      long: _loc.long,
      organization: 1,
      type: "PB"
    });
    console.log(resp);
    this._title = "";
    this._description = "";
    this._date = "";
    this._loc = {};
  };
}

export const eventFormStore = new EventFormStore();
