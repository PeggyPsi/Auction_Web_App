export class DateCompare {
    static dateCompare(startDate: string, endDate: string): number {
        const start: string[] = startDate.split('T',2); 
        const end: string[] = endDate.split('T',2); 

        if (start[0] < end[0]) {
            return -1;
        }
        else if (start[0] === end[0]){
            if(start[1] < end[1]){
                return -1;
            } 
            else if(start[1] === end[1]) {
                return 0;
            }
        }
        else {
            return 1;
        }
    }
}
