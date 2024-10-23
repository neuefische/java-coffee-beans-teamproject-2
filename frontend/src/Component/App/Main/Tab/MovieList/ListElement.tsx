import MovieRatingType from "../../../../../Type/MovieRatingType.tsx";
import {useNavigate} from "react-router-dom";

export default function ListElement({movie}: { movie: MovieRatingType }) {
    const navigate = useNavigate();

    const redirect = function () {
        navigate("/movie/" + movie.movieId);
    }

    return (
        <>
            <div className={"list-element"}>
                <span className={"movie-name"}>{movie.movieName}</span>
                <span className="movie-rating">{"★".repeat(movie.rating)}</span>
                <span className="empty-stars">{"★".repeat(10 - movie.rating)}</span>
                <button className={"edit-button"} onClick={redirect}>Details</button>
            </div>
        </>
    );
}
