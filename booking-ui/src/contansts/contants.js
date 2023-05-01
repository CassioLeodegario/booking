import img1 from '../assets/image (1).jpeg'
import img2 from '../assets/image (2).jpeg'
import img3 from '../assets/image (3).jpeg'

const API_URL = process.env.REACT_APP_API_URL;

const PLACES_DATA = [
    {
        id: 1,
        imgSrc: img1,
        destTitle: 'Beach House',
        location: 'Maldives',
        address: "Beach Street #122",
        price: "$ 4250",
        discount: "15 %",
        bedrooms: 2,
        baths: 2,
        area: '1250 m2'
    },
    {
        id: 2,
        imgSrc: img2,
        destTitle: 'Quiet Place',
        location: 'Australia',
        address: "Main Avenue #675",
        price: "$ 850",
        discount: "45 %",
        bedrooms: 3,
        baths: 1,
        area: '750 m2'
    },
    {
        id: 3,
        imgSrc: img3,
        destTitle: 'Modern House',
        location: 'London',
        address: "Vine Street #950",
        price: "$ 2250",
        discount: "30 %",
        bedrooms: 1,
        baths: 1,
        area: '822 m2'
    }
]

export { PLACES_DATA, API_URL };