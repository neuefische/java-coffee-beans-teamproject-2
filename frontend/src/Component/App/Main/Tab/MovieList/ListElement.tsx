import MovieRatingType from "../../../../../Type/MovieRatingType.tsx";
import {useNavigate} from "react-router-dom";

export default function ListElement({movie}: { movie: MovieRatingType }) {
    const navigate = useNavigate();

    const redirect = function () {
        navigate("/api/movie/" + movie.movieId);
    }

    return (
        <>
            <div className={"list-element"}>
                <span className={"movie-name"}>{movie.movieName}</span>

                {movie.isWatched ? (
                    <>
                        <div className="movie-rating">
                            <span className="filled-stars">{"★".repeat(movie.rating)}</span>
                            <span className="empty-stars">{"★".repeat(10 - movie.rating)}</span>
                        </div>
                    </>
                ) : (
                    <span className="movie-priority">Low</span>
                )}

                <button className={"edit-button"} onClick={redirect}>Details</button>
            </div>
        </>
    );
}
