import MovieType from "../../../../../Type/MovieType.tsx";
import ListElement from "./ListElement.tsx";


export default function WatchedList({ data }: { data: MovieType[] }) {
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