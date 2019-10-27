import {action, computed, observable} from "mobx";
import axios from 'axios'
import {ENDPOINTS} from "../constants/endpoints";
axios.defaults.baseURL = '/api';
class RestStore {
    @observable _organisationsList = [];
    @observable _organisationEventsList = [];
    @observable userSettings = [];
    _organisationsListTimer = null;
    _organisationsEventsTimer = null;

    addNewEvent(params){
        return axios.post(ENDPOINTS.EVENTS, params)
    }

    @action
    getOrganisations(){
        if (this._organisationsListTimer)
            return
        const handler = async () => {
            const resp = await axios.get(ENDPOINTS.ORGANISATIONS);
            console.log('organisations response',resp);
            this._organisationsList = resp.data || [];
        };
        handler();
        this._organisationsListTimer = setInterval(
            handler,
            30000
        )

    }
    @computed get organisationsNamesList() {
        return this._organisationsList.map(
            org => org.login
        )
    }
    @computed get organisationsList() {
        return this._organisationsList
    }
    @action
    getOrganisationsEvents() {
        if (this._organisationsEventsTimer){
            return
        }
        const handler = async () => {
            const resp = await axios.get(ENDPOINTS.EVENTS)
            console.log('events response', resp);
            this._organisationEventsList = resp.data || []
        }
        handler()
        this._organisationsEventsTimer = setInterval(
            handler, 30000
        )
    }
    @computed get organisationsEvents() {
        return this._organisationEventsList
    }

    @computed get organisationsEventsNames() {
        return this._organisationEventsList.map(e => e.title)
    }
    cancelScheduleOrganisationsRequests() {
        clearInterval(this._organisationsListTimer)
    }
    cancelScheduleOrganisationsEventsRequests() {
        clearInterval(this._organisationsEventsTimer)
    }
    @computed get addressPoints(){
        return this.organisationsEvents.map(
            ({lat, long, title}) => ({lat,long, title})
        )
    }
}
export const rest = new RestStore();
