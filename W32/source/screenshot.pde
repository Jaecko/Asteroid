import java.io.FilenameFilter;

void screenShot(){  
  
   File f = dataFile("screenshots");
   String[] names = f.list(FILTER);
   //printArray(names);
   
   //printArray(f);
   int nb_file = names.length;
   
   if(nb_file < 10){
     saveFrame("data/screenshots/screenshot-0"+(nb_file+1)+".png");
   }else{
     saveFrame("data/screenshots/screenshot-"+(nb_file+1)+".png");
   } 
   
}


static final FilenameFilter FILTER = new FilenameFilter() {
  static final String NAME = "screenshot", EXT = ".png";
   
  @ Override boolean accept(File path, String name) {
    name = name.toLowerCase();
    return name.startsWith(NAME) && name.endsWith(EXT);
  }
};
