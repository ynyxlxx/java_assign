// Student name: 
// Student ID  : 

// Submission deadline: Friday, 28 June, 4 pm

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Comparator;
import java.util.Scanner;

public class Tut_03 
{
    public static void main(String[] args) // DO NOT modify the main() method
    {
        String fname = "videoData.txt";
        ArrayList<VideoRec> list = readDataFile(fname);        
        
        System.out.println("Top 10 most popular videos (Video ID, view count):");
        
        List<Pair<String, Integer>> top10 = findTop10Video(list);

        for (Pair<String, Integer> p : top10)
            System.out.println(p);        
        
        /* Expected output:
        Top 10 most popular videos (Video ID, view count):
        (SEknH0jt, 356)
        (m6fTBdej, 355)
        (t0H9hlqb, 346)
        (qWc0uJ4K, 337)
        (OOEmaeFm, 327)
        (n9IofCpB, 317)
        (PueHJlUX, 298)
        (RJL2Ncb3, 277)
        (HX3a94A6, 252)
        (OIV4FjWb, 251)
        */
    }
    
    private static ArrayList<VideoRec> readDataFile(String fname)
    {
        // Read in the VideoRec from data file
        ArrayList<VideoRec> list = new ArrayList();
        
        try (Scanner sc = new Scanner(new File(fname)))
        {
            while (sc.hasNextLine())
            {
                String line = sc.nextLine();
                String[] token = line.split(",");
                list.add(new VideoRec(Long.parseLong(token[0]), token[1], token[2]));   
            }
        }
        catch(FileNotFoundException e)
        {
            System.out.println(e.getMessage());
        }         
        return list;
    }    
    
    private static List<Pair<String, Integer>> findTop10Video(ArrayList<VideoRec> list)
    {

        // Your codes.

        // Sort the input list by vid, and then count the number of times each vid is viewed.
        // Return a List<Pair<String, Integer>> with up to 10 records (video ID, viewCount).

        // List is an interface.
        // ArrayList is a concrete class that implements the List interface.

        Comparator cmp = new Comparator<VideoRec>() {
            public int compare(VideoRec v1, VideoRec v2)
            {
                return v1.getVid().compareTo(v2.getVid());
            }
        };
        list.sort(cmp);

        ArrayList<Pair<String, Integer>> vidcount = new ArrayList<>();
        int count = 1;
        for (int j = 0; j < list.size(); j++) {
            if (j == list.size() - 1) {

                if (list.get(j).getVid().equals(list.get(j - 1).getVid())) {
                    count = count + 1;
                    vidcount.add(new Pair<>(list.get(j).getVid(), count));
                }else{
                    vidcount.add(new Pair<>(list.get(j).getVid(), 1));
                }

            } else {
                if (list.get(j + 1).getVid().equals(list.get(j).getVid())) {
                    count = count + 1;
                } else {
                    vidcount.add(new Pair<>(list.get(j).getVid(), count));
                    count = 1;
                }
            }
        }

        Comparator c = new Comparator<Pair<String, Integer>>(){
            public int compare(Pair<String, Integer> p1, Pair<String, Integer> p2)
            {
                return p2.getSecond().compareTo(p1.getSecond());
            }
        };
        vidcount.sort(c);

        List<Pair<String, Integer>> result = new ArrayList(){};

        for (int m = 0; m < 10; m++)
            result.add(vidcount.get(m));

        return result;  // dummy return statement
    }    
}

