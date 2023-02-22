/**
 *
 * @author Ouda
 */

//importing the libraries that will be needed in this program

import javax.swing.*;
import java.awt.*;
import java.util.Calendar;
import java.util.Random;

/*

This assignment was completed by Zachary Tidwell
I received assistance from the TA Nishanth Goud Pendimukulla during his office hours
I also collaborated with Michael Skrzynski on some portions of the assignment
2/15/2023

*/

//The class that has all the sorts in it
public class SortShow extends JPanel { 

	
		// An array to hold the lines_lengths to be sorted
		public int[] lines_lengths;
		//The amount of lines needed
		public final int total_number_of_lines = 256;
		 // An array to holds the scrambled lines_lengths
		public int[] scramble_lines;
		//A temp Array that is used later for sorts
		public int[] tempArray;
		
		//the default constructor for the SortShow class
		public SortShow(){
			//assigning the size for the lines_lengths below
			lines_lengths = new int[total_number_of_lines];
			for(int i = 0; i < total_number_of_lines; i++) 
				lines_lengths[i] =  i+5;
			
		}
		

		//A method that scrambles the lines
		public void scramble_the_lines(){
			//A random generator
			Random num = new Random(); 
			//Randomly switching the lines
			for(int i = 0; i < total_number_of_lines; i++){
				//getting a random number using the nextInt method (a number between 0 to i + 1)
				int j = num.nextInt(i + 1); 
				//swapping The element at i and j 
				swap(i, j);
			}
			//assigning the size for the scramble_lines below
			scramble_lines = new int[total_number_of_lines];
			//copying the now scrambled lines_lengths array into the scramble_lines array 
			//to store for reuse for other sort methods
			//so that all sort methods will use the same scrambled lines for fair comparison 
			System.arraycopy(lines_lengths, 0, scramble_lines, 0, total_number_of_lines);
			//Drawing the now scrambled lines_lengths
			paintComponent(this.getGraphics());
		}
		
		//Swapping method that swaps two elements in the lines_lengths array
		public void swap(int i, int j){
			//storing the i element in lines_lengths in temp
			int temp = lines_lengths[i];
			//giving i element in lines_lengths the value of j element in lines_lengths
			lines_lengths[i] = lines_lengths[j];
			//giving j element in lines_lengths the value of temp
			lines_lengths[j] = temp;
		}
		
		//The selectionSort method
		public void SelectionSort(){
			//getting the date and time when the selection sort starts
			Calendar start = Calendar.getInstance();
			//Using the selection sort to lines_lengths sort the array
			//You need to complete this part.

				for (int index = 0; index < total_number_of_lines - 1; index++) {
					int indexOfNextSmallest = getIndexOfSmallest(index, total_number_of_lines - 1);
					//swapping current index with minimum element after current index
					swap(index, indexOfNextSmallest);
					//updating the graphic
					paintComponent(this.getGraphics());
					delay(10);
				}

			//getting the date and time when the selection sort ends
			Calendar end = Calendar.getInstance();
			//getting the time it took for the selection sort to execute 
			//subtracting the end time with the start time
	        SortGUI.selectionTime = end.getTime().getTime() - start.getTime().getTime();
		}
		
		//this method gets the smallest element in the array of lines_lengths
		public int getIndexOfSmallest(int first, int last){

			//You need to complete this part.
			int min = lines_lengths[first];
			int indexOfMin = first;
			for(int j = first + 1; j <= last; j++)
			{
				if(lines_lengths[j] < min)
				{
					min = lines_lengths[j];
					indexOfMin = j;
				}
			}

			return indexOfMin; //returning index of the next minimum element
		}
		
	///////////////////////////////////////////////////////////////////////////////////
		
		//recursive merge sort method
		public void R_MergeSort(){
			//getting the date and time when the recursive merge sort starts
			Calendar start = Calendar.getInstance();
			//assigning the size for the tempArray below

			tempArray = new int[total_number_of_lines];
			R_MergeSort(0, 255);

			Calendar end = Calendar.getInstance();
			//getting the time it took for the iterative merge sort to execute
			//subtracting the end time with the start time
	        SortGUI.rmergeTime = end.getTime().getTime() - start.getTime().getTime();
			
		}
		
		//recursive merge sort method
		public void R_MergeSort(int first, int last){
			if(first < last){

				//You need to complete this part.

				int mid = (first+last)/2;
				R_MergeSort(first, mid);
				R_MergeSort(mid+1, last);

				R_Merge(first, mid, last);

				//Causing a delay for 10ms
				delay(10); 
			}
		}

		
		//recursive merge sort method
		public void R_Merge(int first, int mid, int last){

			//You need to complete this part.
			int beginHalf1 = first;
			int endHalf1 = mid;
			int beginHalf2 = mid + 1;
			int endHalf2 = last;

			int index = beginHalf1;

			while((beginHalf1 <= endHalf1) && (beginHalf2 <= endHalf2))
			{
				if (lines_lengths[beginHalf1] < lines_lengths[beginHalf2])
				{
					tempArray[index] = lines_lengths[beginHalf1];
					beginHalf1++;
				} else
				{
					tempArray[index] = lines_lengths[beginHalf2];
					beginHalf2++;
				}
				index++;
			}

			for (; beginHalf1 <= endHalf1; beginHalf1++, index++)
				// Invariant: tempArray[beginHalf1..index-1] is in order
				tempArray[index] = lines_lengths[beginHalf1];

			// Finish off the second sub-array, if necessary
			for (; beginHalf2 <= endHalf2; beginHalf2++, index++)
				// Invariant: tempArray[beginHalf1..index-1] is in order
				tempArray[index] = lines_lengths[beginHalf2];

			// Copy the result back into the original array
			for (index = first; index <= last; index++)
				lines_lengths[index] = tempArray[index];

			paintComponent(this.getGraphics());
		}
		
		//

	//////////////////////////////////////////////////////////////////////////////////////////
		
		//iterative merge sort method
		public void I_MergeSort()
		{
		//getting the date and time when the iterative merge sort starts
		Calendar start = Calendar.getInstance();
		//assigning the size for the tempArray below
		tempArray = new int[total_number_of_lines]; 
		//saving the value of total_number_of_lines
		int beginLeftovers = total_number_of_lines;

		
		for (int segmentLength = 1; segmentLength <= total_number_of_lines/2; segmentLength = 2*segmentLength)
		{
			beginLeftovers = I_MergeSegmentPairs(total_number_of_lines, segmentLength);
			int endSegment = beginLeftovers + segmentLength - 1;
			if (endSegment < total_number_of_lines - 1) 
			{
			I_Merge(beginLeftovers, endSegment, total_number_of_lines - 1);
			}
		} 

		// merge the sorted leftovers with the rest of the sorted array
		if (beginLeftovers < total_number_of_lines) {
			I_Merge(0, beginLeftovers-1, total_number_of_lines - 1);
		}
		//getting the date and time when the iterative merge sort ends
		Calendar end = Calendar.getInstance();
		//getting the time it took for the iterative merge sort to execute 
		//subtracting the end time with the start time
	    SortGUI.imergeTime = end.getTime().getTime() - start.getTime().getTime();
	} 

	// Merges segments pairs (certain length) within an array 
	public int I_MergeSegmentPairs(int l, int segmentLength)
	{
		//The length of the two merged segments 

		//You suppose  to complete this part (Given).
		int mergedPairLength = 2 * segmentLength;
		int numberOfPairs = l / mergedPairLength;

		int beginSegment1 = 0;
		for (int count = 1; count <= numberOfPairs; count++)
		{
			int endSegment1 = beginSegment1 + segmentLength - 1;

			int beginSegment2 = endSegment1 + 1;
			int endSegment2 = beginSegment2 + segmentLength - 1;
			I_Merge(beginSegment1, endSegment1, endSegment2);

			beginSegment1 = endSegment2 + 1;
			//redrawing the lines_lengths
			paintComponent(this.getGraphics());
			//Causing a delay for 10ms
			delay(10);
		}
		// Returns index of last merged pair
		return beginSegment1;
		//return 1;//modify this line
	}

	public void I_Merge(int first, int mid, int last)
	{
		//You suppose  to complete this part (Given).
		// Two adjacent sub-arrays
		int beginHalf1 = first;
		int endHalf1 = mid;
		int beginHalf2 = mid + 1;
		int endHalf2 = last;

		// While both sub-arrays are not empty, copy the
		// smaller item into the temporary array
		int index = beginHalf1; // Next available location in tempArray
		for (; (beginHalf1 <= endHalf1) && (beginHalf2 <= endHalf2); index++)
		{
			// Invariant: tempArray[beginHalf1..index-1] is in order
			if (lines_lengths[beginHalf1] < lines_lengths[beginHalf2])
			{
				tempArray[index] = lines_lengths[beginHalf1];
				beginHalf1++;
			}
			else
			{
				tempArray[index] = lines_lengths[beginHalf2];
				beginHalf2++;
			}
		}
		//redrawing the lines_lengths
		paintComponent(this.getGraphics());

		// Finish off the nonempty sub-array

		// Finish off the first sub-array, if necessary
		for (; beginHalf1 <= endHalf1; beginHalf1++, index++)
			// Invariant: tempArray[beginHalf1..index-1] is in order
			tempArray[index] = lines_lengths[beginHalf1];

		// Finish off the second sub-array, if necessary
		for (; beginHalf2 <= endHalf2; beginHalf2++, index++)
			// Invariant: tempArray[beginHalf1..index-1] is in order
			tempArray[index] = lines_lengths[beginHalf2];

		// Copy the result back into the original array
		for (index = first; index <= last; index++)
			lines_lengths[index] = tempArray[index];
	}

	//bubbleSort method
	public void bubbleSort(){
		Calendar start = Calendar.getInstance();
		for(int i = 0; i < total_number_of_lines - 1; ++i) {
			paintComponent(this.getGraphics());
			delay(10);
			for(int j = 0; j < total_number_of_lines - i - 1; ++j) {
				if(lines_lengths[j] > lines_lengths[j+1]) {
					swap(j, j+1);
				}
			}
		}
		Calendar end = Calendar.getInstance();
		//getting the time it took for the bubble sort to execute
		//subtracting the end time with the start time
		SortGUI.bubbleTime = end.getTime().getTime() - start.getTime().getTime();
	}

	public void insertionSort(){
		Calendar start = Calendar.getInstance();

		for(int j = 1; j < total_number_of_lines; j++){
			paintComponent(this.getGraphics());
			delay(10);
			int key = lines_lengths[j];
			int i = j - 1;
			while((i > -1) && (lines_lengths[i] > key)){
				lines_lengths[i+1] = lines_lengths[i];
				i--;
			}
			lines_lengths[i+1] = key;
		}

		Calendar end = Calendar.getInstance();
		//getting the time it took for the bubble sort to execute
		//subtracting the end time with the start time
		SortGUI.insertionTime = end.getTime().getTime() - start.getTime().getTime();
	}


	public void shellSort(){
		Calendar start = Calendar.getInstance();
		shellSort(0,255);
		Calendar end = Calendar.getInstance();
		//getting the time it took for the bubble sort to execute
		//subtracting the end time with the start time
		SortGUI.shellTime = end.getTime().getTime() - start.getTime().getTime();
	}

	//shellSort method
	public void shellSort(int first, int last){
		for(int space = total_number_of_lines / 2; space > 0; space = space / 2){
			for(int begin = first; begin < first + space; begin++)
				incrementalInsertionSort(begin,last,space);
		}
	}
	//shellSort helper method that uses insertion sort
	public void incrementalInsertionSort(int first, int last, int space){
		int unsorted, index;
		for(unsorted = first+space; unsorted <= last; unsorted = unsorted+space){
			int firstUnsorted = lines_lengths[unsorted];
			for(index = unsorted - space; (index >= first) && (firstUnsorted < lines_lengths[index]); index = index - space){
				lines_lengths[index+space] = lines_lengths[index];
			}
			lines_lengths[index+space] = firstUnsorted;
		}
		paintComponent(this.getGraphics());
		delay(10);
	}
	//quickSort method
	public void quickSort(){
		Calendar start = Calendar.getInstance();
		quickSort(0,255);
		Calendar end = Calendar.getInstance();
		//getting the time it took for the bubble sort to execute
		//subtracting the end time with the start time
		SortGUI.quickTime = end.getTime().getTime() - start.getTime().getTime();
	}

	//alternate method for quickSort
	public void quickSort(int first, int last){
			if(first < last){
				int partitionIndex = partition(first, last);

				quickSort(first, partitionIndex - 1);
				quickSort(partitionIndex + 1, last);
			}
	}

	//quickSort helper method that finds a pivot and partitions along the pivot
	public int partition(int first, int last){
			int pivot = lines_lengths[last];
			int i = first - 1;

			for(int j = first; j < last; j++){
				if(lines_lengths[j] <= pivot){
					i++;
					swap(i,j);
				}
			}
			swap(i+1, last);
			paintComponent(this.getGraphics());
			delay(10);

			return i+1;
	}

	//////////////////////////////////////////////////////////////////////	
		
		//This method resets the window to the scrambled lines display
		public void reset(){
			if(scramble_lines != null)
			{
				//copying the old scrambled lines into lines_lengths
				System.arraycopy(scramble_lines, 0, lines_lengths, 0, total_number_of_lines);
			//Drawing the now scrambled lines_lengths
			paintComponent(this.getGraphics());
		}
			}
		
	
		//This method colours the lines and prints the lines
		public void paintComponent(Graphics g){
 			super.paintComponent(g);
			//A loop to assign a colour to each line
			for(int i = 0; i < total_number_of_lines; i++){
				//using eight colours for the lines
				if(i % 8 == 0){
					g.setColor(Color.green);
				} else if(i % 8 == 1){
					g.setColor(Color.blue);
				} else if(i % 8 == 2){
					g.setColor(Color.yellow);
				} else if(i%8 == 3){
					g.setColor(Color.red);
				} else if(i%8 == 4){
					g.setColor(Color.black);
				} else if(i%8 == 5){
					g.setColor(Color.orange);
				} else if(i%8 == 6){
					g.setColor(Color.magenta);
				} else
					g.setColor(Color.gray);
				
				//Drawing the lines using the x and y-components 
				g.drawLine(4*i + 25, 300, 4*i + 25, 300 - lines_lengths[i]);
			}
			
		}
		
		//A delay method that pauses the execution for the milliseconds time given as a parameter
		public void delay(int time){
			try{
	        	Thread.sleep(time);
	        }catch(InterruptedException ie){
	        	Thread.currentThread().interrupt();
	        }
		}
		
	}

