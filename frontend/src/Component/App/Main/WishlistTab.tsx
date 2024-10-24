
import WishList from "./Tab/MovieList/WishList.tsx";
import MovieRatingType from "../../../Type/MovieRatingType.tsx";

export default function WishlistTab({data}: { data:MovieRatingType[] }) {

    return (
        <div className={"wishlistTab"}>
            <h2>To be watched movies:</h2>
            <WishList data={data}/>
        </div>
    );
}