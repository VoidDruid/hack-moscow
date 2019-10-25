import React, { useState } from 'react'
import axios, { AxiosResponse } from 'axios'
const App: React.FC = () => {
  const [context, setContext] = useState('')
  const handleClick: () => void = async () => {
    const resp: AxiosResponse = await axios.get('/api')
    setContext(JSON.stringify(resp))
  }
  return (
    <div className="App">
      <button onClick={handleClick}>click me</button>
      <div>{context}</div>
    </div>
  )
}

export default App
