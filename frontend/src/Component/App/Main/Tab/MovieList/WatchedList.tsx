import ListElement from "./ListElement.tsx";
import MovieRatingType from "../../../../../Type/MovieRatingType.tsx";


export default function WatchedList({ data }: { data: MovieRatingType[] }) {
    return (
        <div>
            MovieList
            <div>
                {data?.map(
                    (movie) => <ListElement key={movie.id} movie={movie} />
                )}
            </div>
        </div>
    );
}