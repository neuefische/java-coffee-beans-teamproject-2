import WatchedTab from "./Main/WatchedTab.tsx";
import WishlistTab from "./Main/WishlistTab.tsx";
import EditMovieForm from "./EditMovieForm.tsx";
import CreateButton from "./Main/CreateButton.tsx";
import {useEffect, useState} from "react";
import MovieType from "../../Type/MovieType.tsx";
import RatingType from "../../Type/RatingType.tsx";
import axios from "axios";
import MovieRatingType from "../../Type/MovieRatingType.tsx";
import PersonType from "../../Type/PersonType.tsx";

export default function Main() {
    const [wishListData, setWishListData] = useState<MovieRatingType[]>([]);
    const [watchedListData, setwatchedListData] = useState<MovieRatingType[]>([]);

    const [isUpdated, setIsUpdated] = useState(false);

    const [actorsData, setActorsData] = useState<PersonType[]>([]);
    const [directorsData, setDirectorsData] = useState<PersonType[]>([]);
    useEffect(() => {
        axios.get<MovieRatingType[]>("/api/movie/wishlist").then(
            (result) => {
                setWishListData(result.data)
            }
        ).catch(
            () => console.log("Something went wrong")
        )
        axios.get<MovieRatingType[]>("/api/movie/watched").then(
            (result) => {
                setwatchedListData(result.data)
            }
        ).catch(
            () => console.log("Something went wrong")
        )
        setIsUpdated(false);
        setMovieData({
            id: "",
            name: ""
        })
        setRatingData({
            id: "",
            rating: 0,
            isWatched: true,
            movieId: ""
        })
    }, [isUpdated]);

    const [movieData, setMovieData] = useState<MovieType>({
        id: "",
        name: ""
    });
    const [ratingData, setRatingData] = useState<RatingType>({
        id: "",
        rating: 0,
        isWatched: true,
        movieId: ""
    });


    return (
        <>
            <div className={"main_container"}>
                <WatchedTab data={watchedListData}/>
                <WishlistTab data={wishListData}/>
            </div>
            <div className={"edit-form-main"}>
                <EditMovieForm movieData={movieData} ratingData={ratingData} setMovieData={setMovieData}
                               setRatingData={setRatingData} actorsData={actorsData}
                               setActorsData={setActorsData} directorsData={directorsData}
                               setDirectorsData={setDirectorsData}/>
                <CreateButton movieData={movieData} ratingData={ratingData} setIsUpdated={setIsUpdated} actorData={actorsData} directorData={directorsData}/>
            </div>
        </>
    );
}