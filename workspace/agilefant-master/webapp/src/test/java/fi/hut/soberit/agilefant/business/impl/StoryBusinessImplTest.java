package fi.hut.soberit.agilefant.business.impl;

import java.util.HashSet;
import java.util.Set;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;

import fi.hut.soberit.agilefant.model.Iteration;
import fi.hut.soberit.agilefant.model.Story;
import fi.hut.soberit.agilefant.model.StoryRank;

public class StoryBusinessImplTest {

    private StoryBusinessImpl impl;
    
    private Story s1;
    private Story s2; 

    
    @Before
    public void setup() {
        impl = new StoryBusinessImpl();
        
        s1 = new Story();
        s2 = new Story();
        s1.setId(1);
        s2.setId(2);
    }
    

    @Test
    public void iterationChanged_same() {
            
        final Iteration ite = new Iteration();
        ite.setId(3);
        
        s1.setIteration(ite);
        s2.setIteration(ite);
        
        Assert.assertFalse(StoryBusinessImpl.haveDifferentIteration(s1, s2));
    }

    
    @Test
    public void iterationChanged_null() {
        Assert.assertTrue(StoryBusinessImpl.haveDifferentIteration(null, s1));
        Assert.assertTrue(StoryBusinessImpl.haveDifferentIteration(s1, null));
        
        Assert.assertFalse(StoryBusinessImpl.haveDifferentIteration(null, null));
    }

    
    @Test
    public void iterationChanged() {
        final Iteration ite = new Iteration();
        ite.setId(3);

        final Iteration ite2 = new Iteration();
        ite2.setId(4);
        
        s1.setIteration(ite);
        s2.setIteration(ite2);
        
        Assert.assertTrue(StoryBusinessImpl.haveDifferentIteration(s1, s2));
    }
    
     
    @Test
    public void storyHasChildren() {
        Assert.assertFalse(StoryBusinessImpl.storyHasChildren(null));
        
        
        // initialization
        s1.getChildren().add(s2);
        
        // test
        Assert.assertTrue(StoryBusinessImpl.storyHasChildren(s1));
    }
    
    
}
