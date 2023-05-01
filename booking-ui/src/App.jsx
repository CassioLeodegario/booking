import React, { useRef } from 'react';
import './app.css';
import Home from './components/Home/Home';
import Offers from './components/Offers/Offers';
import About from './components/About/About';

const App = ({setTransparent}) => {
    const scrollRef = useRef(null);

    return (
        <>
            <Home setTransparent={setTransparent} scrollRef={scrollRef} />
            <Offers scrollRef={scrollRef} />
            <About />
        </>
    )
}

export default App;