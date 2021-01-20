
import java.io.*; 
import java.util.*;
import java.text.*;
class TodoAttirbutes{
	public String help(){
		return "Usage :-\n$ ./todo add \"todo item\"  # Add a new todo\n$ ./todo ls               # Show remaining todos\n$ ./todo del NUMBER       # Delete a todo\n$ ./todo done NUMBER      # Complete a todo\n$ ./todo help             # Show usage\n$ ./todo report           # Statistics";
	}
	public void add(String[] args){
		try{
		if(args.length==1){
			System.out.println("Error: Missing todo string. Nothing added!");
		}
		else{
			int count = 0;
			for(int i=1;i<args.length;i++){
				System.out.println("Added todo: \""+args[i]+"\"");
				count++;
				String s = String.valueOf(args[i])+"\n";
				Writer output = new BufferedWriter(new FileWriter("Todo.txt", true));
				output.append(s);
				output.close();
				}
			
			}

		}
		catch(Exception e){
			System.out.println(e);
		}
	}
	public void list(){
		try{
			FileReader fr=new FileReader("todo.txt");
			BufferedReader br=new BufferedReader(fr);
			String s;
			List<String> tmp = new ArrayList<String>();
			do{
				s = br.readLine();
				tmp.add(s);
			}while(s!=null);
			if(tmp.get(tmp.size()-1)==null)
				tmp.remove(tmp.size()-1);
			int count = tmp.size();
			String out = "";
			if(tmp.size()>0){
				for(int i=tmp.size()-1;i>=0;i--) {
					out += "["+count+"] "+tmp.get(i)+"\n";
					count--;
				}
				System.out.println(out);
			}
			else{
				System.out.println("There are no pending todos!");
			}
		}
		catch(Exception e){
			System.out.println("There are no pending todos!");
		}

	}
	public void delete(String[] args){
		try{
			File inputFile = new File("Todo.txt");
			File tempFile = new File("myTempFile.txt");
	
			BufferedReader reader = new BufferedReader(new FileReader(inputFile));
			BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile));
			List <Integer>tmp = new ArrayList <Integer>();
			for(int i=1;i<args.length;i++)
				tmp.add(Integer.valueOf(args[i]));
			Collections.sort(tmp);
			if(tmp.size()==1){
				String currentLine;
				int count = 0;
				boolean flag = false;
				while((currentLine = reader.readLine()) != null) {
					// trim newline when comparing with lineToRemove
					count++;
					String trimmedLine = currentLine.trim();
					if(tmp.contains(count)){flag = true; continue; }
					writer.write(currentLine + System.getProperty("line.separator"));
				}
				inputFile.delete();
				reader.close();
				writer.close();
				inputFile.delete();
				boolean successful = tempFile.renameTo(inputFile);
				if(flag)
					System.out.println("Deleted todo #"+tmp.get(0));
				else
					System.out.println("Error: todo #"+tmp.get(0)+" does not exist. Nothing deleted.");
			}
			else
			{
				String currentLine;
				int count = 0;
				while((currentLine = reader.readLine()) != null) {
					// trim newline when comparing with lineToRemove
					count++;
					String trimmedLine = currentLine.trim();
					if(tmp.contains(count)) continue;
					writer.write(currentLine + System.getProperty("line.separator"));
				}
				inputFile.delete();
				reader.close();
				writer.close();
				inputFile.delete();
				boolean successful = tempFile.renameTo(inputFile);
			}
		}
		catch(Exception e){
			System.out.println("Error: Missing NUMBER for deleting todo.");
		}

	}
	public void done(String[] args){
		try{
			File inputFile = new File("todo.txt");
			File tempFile = new File("myTempFile.txt");
	
			BufferedReader reader = new BufferedReader(new FileReader(inputFile));
			BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile));
			List <Integer>tmp = new ArrayList <Integer>();
			for(int i=1;i<args.length;i++)
				tmp.add(Integer.valueOf(args[i]));
			Collections.sort(tmp);
			if(tmp.size()==1){
				String currentLine;
				int count = 0;
				boolean flag = false;
				while((currentLine = reader.readLine()) != null) {
					// trim newline when comparing with lineToRemove
					count++;
					String trimmedLine = currentLine.trim();
					if(tmp.contains(count)){
						flag = true; 
						Writer output = new BufferedWriter(new FileWriter("done.txt", true));
						Date date = new Date();
						DateFormat dateFormat=new SimpleDateFormat("yyyy-MM-dd");
						String formattedDate=dateFormat.format(date);
						output.append("x "+formattedDate+" "+currentLine+System.getProperty("line.separator"));
						output.close();
						continue; 
					}
					writer.write(currentLine + System.getProperty("line.separator"));
				}
				inputFile.delete();
				reader.close();
				writer.close();
				inputFile.delete();
				boolean successful = tempFile.renameTo(inputFile);
				if(flag)
					System.out.println("Marked todo #"+tmp.get(0)+" as done.");
				else
					System.out.println("Error: todo #"+tmp.get(0)+" does not exist.");
			}
			else if(args.length==1){
				System.out.println("Error: Missing NUMBER for marking todo as done.");
			}
			else
			{
				String currentLine;
				int count = 0;
				while((currentLine = reader.readLine()) != null) {
					// trim newline when comparing with lineToRemove
					count++;
					String trimmedLine = currentLine.trim();
					if(tmp.contains(count)){continue;}
					writer.write(currentLine + System.getProperty("line.separator"));
				}
				inputFile.delete();
				reader.close();
				writer.close();
				inputFile.delete();
				boolean successful = tempFile.renameTo(inputFile);
			}
		}
		catch(Exception e){
			System.out.println("Error: Missing NUMBER for marking todo as done.");
		}		

	}
	public void report(){
		try{
			File file1 = new File("todo.txt");
			File file2 = new File("done.txt");
			int total=0,complete=0;
			Scanner sc = new Scanner(file1);
			while(sc.hasNextLine()) {
				sc.nextLine();
				total++;
			}
			sc.close();
			sc = new Scanner(file2);
			while(sc.hasNextLine()) {
				sc.nextLine();
				complete++;
			}
			sc.close();
			Date date = new Date();
			DateFormat dateFormat=new SimpleDateFormat("yyyy-MM-dd");
			String formattedDate=dateFormat.format(date);
			System.out.println(formattedDate+" Pending : "+total+" Completed : "+complete);
		}catch(Exception e){
			Date date = new Date();
			DateFormat dateFormat=new SimpleDateFormat("yyyy-MM-dd");
			String formattedDate=dateFormat.format(date);
			System.out.println(formattedDate+" Pending : "+0+" Completed : "+0);
		}

	}
}
public class Todo {
	public static void main(String args[])throws IOException{
		try{
			if(args.length==0||args[0].equals("help")){
				TodoAttirbutes ta  = new TodoAttirbutes();
				System.out.println(ta.help());			
			}
			else if(args[0].equals("add")){
				TodoAttirbutes ta = new TodoAttirbutes();
				ta.add(args);
			}
			else if(args[0].equals("ls")){
				TodoAttirbutes ta = new TodoAttirbutes();
				ta.list();			
			}
			else if(args[0].equals("del")){
				TodoAttirbutes ta = new TodoAttirbutes();
				ta.delete(args);
			}
			else if(args[0].equals("done")){
				TodoAttirbutes ta = new TodoAttirbutes();
				ta.done(args);
			}
			else if(args[0].equals("report")){
				TodoAttirbutes ta = new TodoAttirbutes();
				ta.report();
			}
			else{
				System.out.println("Usage :-\n$ ./todo add \"todo item\"  # Add a new todo\n$ ./todo ls               # Show remaining todos\n$ ./todo del NUMBER       # Delete a todo\n$ ./todo done NUMBER      # Complete a todo\n$ ./todo help             # Show usage\n$ ./todo report           # Statistics");
			}
		}
		catch(Exception e){
		System.out.println(e);
		System.out.println("Usage :-");
		System.out.println("$ ./todo add \"todo item\"  # Add a new todo");
		System.out.println("$ ./todo ls               # Show remaining todos");
		System.out.println("$ ./todo del NUMBER       # Delete a todo");
		System.out.println("$ ./todo done NUMBER      # Complete a todo");
		System.out.println("$ ./todo help             # Show usage");
		System.out.println("$ ./todo report           # Statistics");
		}
	}
}
