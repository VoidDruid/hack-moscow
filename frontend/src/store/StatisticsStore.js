import { action, computed, observable } from "mobx";
import { rest } from "./RestStore";

class StatisticsStore {
  @observable _stats = [];
  statisticsFetchTimer;
  @action
  getStats() {
    const func = async () => {
      const resp = await rest.getStats();
      const _stats = resp.data.Moscow;
      const stats = [];
      _stats.forEach(stat => {
        const name = Object.keys(stat)[0];
        const { location, count, category } = stat[name];
        stats.push({
          name,
          location,
          count,
          category
        });
      });
      this._stats = stats;
    };
    func();
    this.statisticsFetchTimer = setInterval(func, 300000);
  }
  @computed
  get stats() {
    return this._stats;
  }
  @computed
  get heatPoints() {
    return (
      (this._stats &&
        this._stats.map(({ count, location }) => [
          location.lat,
          location.long,
          count
        ])) ||
      []
    );
  }
}

export const statisticsStore = new StatisticsStore();
