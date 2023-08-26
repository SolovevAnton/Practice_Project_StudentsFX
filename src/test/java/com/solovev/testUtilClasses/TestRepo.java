package com.solovev.testUtilClasses;

import com.solovev.repositories.AbstractRepository;

public class TestRepo extends AbstractRepository<String> {
    public TestRepo(){
        super("notExistentLink","/test");
    }
}

