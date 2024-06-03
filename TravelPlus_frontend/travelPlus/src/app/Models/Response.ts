

export interface Response<T>{
    totalCount: number;
    message:string;
    content :T;
}