import React from 'react'
import {observer} from 'mobx-react'
import {statisticsStore} from '../../store/StatisticsStore';
import StatisticsCard from "./StatisticsCard/StatisticsCard";
import {mapStore} from "../../store/MapStore";
import "./style.css"
@observer
class StatisticsPage extends React.Component {
    componentDidMount() {
        statisticsStore.getStats();
        mapStore.activateHeatMap()
    }

    componentWillUnmount() {
        mapStore.deactivateHeatMap();
    }

    render() {
        return <div className="statistics-container">
            <header>
                <h2>
                Statistics
                </h2>
            </header>
            <main>
                <div className="statistics-list">
                    {
                        statisticsStore.stats && statisticsStore.stats.map(
                            stat =>{
                               return  <StatisticsCard {...stat}/>
                            })

                    }
                </div>
            </main>
        </div>
    }
}

export default StatisticsPage;
