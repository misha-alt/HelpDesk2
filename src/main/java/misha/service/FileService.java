package misha.service;

import misha.Valdator.MyValidator;
import misha.dao.TickedDAO;
import misha.dao.UserDAO;
import misha.domain.MyFile;
import misha.domain.Ticked;
import misha.domain.Tickethistory;
import misha.domain.User;
import misha.repository.FileRepository;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;
import org.apache.http.entity.FileEntity;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;


import javax.activation.MimetypesFileTypeMap;
import java.io.*;
import java.security.Principal;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class FileService {

    private SessionFactory sessionFactory;
    private TickedDAO tickedDAO;
    private UserDAO userDAO;
    private HistoryService historyService;


    @Autowired
    public FileService( SessionFactory sessionFactory, TickedDAO tickedDAO, UserDAO userDAO, HistoryService historyService) {

        this.sessionFactory = sessionFactory;
        this.tickedDAO= tickedDAO;
        this.userDAO = userDAO;
        this.historyService = historyService;


    }

    public void addAndsaveFle(MultipartFile file, int id) throws IOException {


                MyFile myFile = new MyFile();
        if (file!= null && file.getOriginalFilename()!= null) {
            myFile.setFile_name(file.getOriginalFilename());
        } else {
            myFile.setFile_name("defaultFileName");
        }

        if (file != null && !file.isEmpty()) {
           /* myFile.setFileType(file.getContentType());*/
            String fileExtension = FilenameUtils.getExtension(file.getOriginalFilename());
            myFile.setFileType(fileExtension);
            myFile.setImage(file.getBytes());




            saveFile(myFile);

           Ticked ticked= tickedDAO.geTickedById(id);
           ticked.getMyFile().add(myFile);
            Set <Tickethistory> set =ticked.getTickethistories();
            //0_________________________________________
            Tickethistory lastElement = set.toArray(new Tickethistory[0])[set.size() - 1];
            lastElement.getMyFiles().add(myFile);
            historyService.updateRecord(lastElement);


           tickedDAO.updateTcked(ticked);


        } else {
            // обработка случая, когда файл не был загружен
        }
    }

    public void saveFile(MyFile myFile){
        Session session = sessionFactory.getCurrentSession();
        session.save(myFile);
    }

    public void deleteFileUpdateTicked(MyFile myFile, Principal principal,int id2){
       User user = userDAO.findByEmail(principal.getName());
       Ticked ticked= tickedDAO.geTickedById(id2);

      ticked.getMyFile().remove(myFile);
     //deleteFile(myFile);

      //))))))))))))))))))))))0000000000000
        //удаляем файл из сета в истории
        Set <Tickethistory> set =ticked.getTickethistories();
        for(Tickethistory tickethistory:set){
          if(tickethistory.getMyFiles().contains(myFile)){

              tickethistory.setDeletedFilename(myFile.getFile_name()+" deleted");


              tickethistory.getMyFiles().remove(myFile);
          }
        }

        /*Tickethistory lastElement = set.toArray(new Tickethistory[0])[set.size() - 1];
        lastElement.getMyFiles().remove(myFile);*/

      // 000000000000000000000000000000000000000000000
      tickedDAO.updateTcked(ticked);
   // deleteFile(getFileById(myFile.getId()));


    }

    public void deleteFile(MyFile myFile) {
        Session session = sessionFactory.getCurrentSession();
        session.delete(myFile);

    }
    public List <MyFile> takeFile(){

        Query query = sessionFactory.getCurrentSession().createQuery("from MyFile");

        List file = query.list();
        return file;

    }
    public MyFile getFileById(int id){

        Query query = sessionFactory.getCurrentSession().createQuery("from MyFile u where u.id = :id");
        query.setParameter("id", id);
        MyFile myFile = (MyFile) query.list().get(0);

        return myFile;
    }

    public void downloadFileOnPC(int id){
        MyFile fileBytes1 = getFileById(id);
        byte[] fileBytes =  getFileById(id).getImage();

        String fileName = fileBytes1.getFile_name(); // Имя файла полученное из базы данных
        String fileExtension = fileBytes1.getFileType(); // Расширение файла полученное из базы данных

        String homeDir = System.getProperty("user.home"); // получаем домашнюю директорию текущего пользователя
        String path = homeDir + File.separator + "Downloads" + File.separator + fileName; // указываем

        // String filePath = "C:/Users/Philosoph/Desktop/"+"One"+fileName; // Путь для нового файла
        File outputFile = new File(path);
        FileOutputStream fileOut = null;
        try {
            fileOut = new FileOutputStream(outputFile);
            BufferedOutputStream bufferOut = new BufferedOutputStream(fileOut);
            bufferOut.write(fileBytes);
            bufferOut.close();
            fileOut.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }



    }


