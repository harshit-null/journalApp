package com.harshit.journalApp.service;

import com.harshit.journalApp.entity.JournalEntry;
import com.harshit.journalApp.entity.User;
import com.harshit.journalApp.repository.journalEntryRepository;
import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Component
@Slf4j
public class JournalEntryService {

    @Autowired
    private journalEntryRepository journalEntryRepository;
    private ObjectId id;
@Autowired
private UserService userService;


    @Transactional
    public void saveEntry(JournalEntry journalEntry, String userName){
        try {      User user = userService.findByUserName(userName);
            journalEntryRepository.save(journalEntry);
            JournalEntry saved = journalEntryRepository.save(journalEntry);
            user.getJournalEntries().add(saved);
            //used for understanding @Transactional
            // user.setUserName(null);
            userService.saveUser(user);

        } catch (RuntimeException e){
            System.out.println(e);
            throw new RuntimeException("an error occured while saving the entry ", e);
        }

    }

    public void saveEntry(JournalEntry journalEntry){
        journalEntryRepository.save(journalEntry);
    }

    public List<JournalEntry> getAll(){
    return journalEntryRepository.findAll();
    }

    public Optional<JournalEntry> findById(ObjectId id){
        return journalEntryRepository.findById(id);
    }

    public boolean deleteById(ObjectId id, String userName){
        boolean removed = false;
        try {
            User user = userService.findByUserName(userName);
             removed =  user.getJournalEntries().removeIf(x ->x.getId().equals(id));
            if (removed)
            {
                userService.saveUser(user);
                journalEntryRepository.deleteById(id);
            }

        }

        catch (Exception e){
            log.error("error");
            throw new RuntimeException("an error occured while deleting the entry", e);
        }
        return removed;




//    public List<JournalEntry> findByUserName(String username){
//
//
//    }
    }
}

