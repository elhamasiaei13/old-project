package com.parvanpajooh.sample.model.vo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.parvanpajooh.commons.platform.ejb.model.vo.BaseVO;

/**
 * 
 * @author mehrdad
 * 
 */

public class AgentTreeVO extends BaseVO {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private SampleVO agent;

	private ArrayList<AgentTreeVO> subAgents = new ArrayList<AgentTreeVO>();

	private AgentTreeVO parent = null;

	private HashMap<Long, AgentTreeVO> locateById = new HashMap<Long, AgentTreeVO>();
	
	private HashMap<Long, AgentTreeVO> locateByNodeId = new HashMap<Long, AgentTreeVO>();
	
	private HashMap<String, AgentTreeVO> locateByNodeCode = new HashMap<String, AgentTreeVO>();

	private HashMap<String, AgentTreeVO> locateByAccountNumber = new HashMap<String, AgentTreeVO>();

	/**
	 * 
	 * @param agent
	 */
	public AgentTreeVO(SampleVO agent) {
		this.agent = agent;
		
		locateById.put(agent.getId(), this);
		locateByNodeId.put(agent.getNodeId(), this);
		locateByNodeCode.put(agent.getNodeCode(), this);
		locateByAccountNumber.put(agent.getCurrentAccountNumber(), this);
	}

	
	/*public void addSubAgent(GeneralAgentVO root, GeneralAgentVO subAgent) {
		if (locateById.containsKey(root)) {
			locateById.get(root).addSubAgent(subAgent);

		} else {
			addSubAgent(root).addSubAgent(subAgent);
		}
	}*/
	 
	/**
	 * 
	 * @param subAgent
	 * @return
	 */
	public AgentTreeVO addSubAgent(SampleVO subAgent) {
		AgentTreeVO subAgentTree = new AgentTreeVO(subAgent);
		subAgents.add(subAgentTree);
		subAgentTree.parent = this;
		subAgentTree.locateById = this.locateById;
		subAgentTree.locateByNodeId = this.locateByNodeId;
		subAgentTree.locateByNodeCode = this.locateByNodeCode;
		subAgentTree.locateByAccountNumber = this.locateByAccountNumber;

		locateById.put(subAgent.getId(), subAgentTree);
		locateByNodeId.put(subAgent.getNodeId(), subAgentTree);
		locateByNodeCode.put(subAgent.getNodeCode(), subAgentTree);
		locateByAccountNumber.put(subAgent.getCurrentAccountNumber(), subAgentTree);

		return subAgentTree;
	}

	
	/*public AgentTreeVO setAsParent(GeneralAgentVO parentRoot) {
		AgentTreeVO t = new AgentTreeVO(parentRoot);
		t.subAgents.add(this);
		this.parent = t;

		t.locateById = this.locateById;
		t.locateByAccountNumber = this.locateByAccountNumber;

		t.locateById.put(agent.getId(), this);
		t.locateByAccountNumber.put(agent.getCurrentAccountNumber(), this);

		t.locateById.put(parentRoot.getId(), t);
		t.locateByAccountNumber.put(parentRoot.getCurrentAccountNumber(), t);

		return t;
	}*/
	
	/**
	 * 
	 * @return
	 */
	public SampleVO getAgent() {
		return agent;
	}

	/**
	 * 
	 * @param aAgent
	 * @return
	 */
	public AgentTreeVO getTreeVo(SampleVO aAgent) {
		return locateById.get(aAgent.getId());
	}

	/**
	 * 
	 * @param aAgentId
	 * @return
	 */
	public AgentTreeVO getTreeVo(Long aAgentId) {
		return locateById.get(aAgentId);
	}

	/**
	 * 
	 * @param aNodeId
	 * @return
	 */
	public AgentTreeVO getTreeVoByNodeId(Long aNodeId) {
		return locateByNodeId.get(aNodeId);
	}

	/**
	 * 
	 * @param aNodeCode
	 * @return
	 */
	public AgentTreeVO getTreevoByNodeCode(String aNodeCode) {
		return locateByNodeCode.get(aNodeCode);
	}

	/**
	 * 
	 * @param accountNumber
	 * @return
	 */
	public AgentTreeVO getTreeVoByAccountNumber(String accountNumber) {
		return locateByAccountNumber.get(accountNumber);
	}

	/**
	 * 
	 * @return
	 */
	public AgentTreeVO getParent() {
		return parent;
	}

	/**
	 * 
	 * @param root
	 * @return
	 */
	public List<SampleVO> getDirectSubAgents(SampleVO root) {
		List<SampleVO> directSubAgentsList;

		AgentTreeVO tree = getTreeVo(root);

		if (null != tree) {
			directSubAgentsList = getDirectSubAgents(tree);
			
		} else {
			directSubAgentsList = new ArrayList<>();
		}
		return directSubAgentsList;
	}

	/**
	 * 
	 * @param root
	 * @return
	 */
	public List<SampleVO> getDirectSubAgents(AgentTreeVO rootAgentTreeVO) {
		List<SampleVO> directSubAgentsList = new ArrayList<SampleVO>();

		AgentTreeVO tree = rootAgentTreeVO;

		if (null != tree) {
			for (AgentTreeVO thisSubAgentTreeVO : tree.subAgents) {
				directSubAgentsList.add(thisSubAgentTreeVO.agent);
			}
		}
		return directSubAgentsList;
	}
	
	/**
	 * 
	 * @param root
	 * @return
	 */
	public List<SampleVO> getAllSubAgents(SampleVO root) {
		List<SampleVO> allSubAgentsList = new ArrayList<SampleVO>();
		
		AgentTreeVO tree = getTreeVo(root);
		
		if (tree != null) {
			allSubAgentsList = getAllSubAgents(tree);
			
		} else {
			allSubAgentsList = new ArrayList<>();
		}
		
		return allSubAgentsList;
	}
	
	/**
	 * 
	 * @param root
	 * @return
	 */
	public List<SampleVO> getAllSubAgents(AgentTreeVO rootAgentTreeVO) {
		List<SampleVO> allSubAgentsList = new ArrayList<SampleVO>();
		
		AgentTreeVO tree = rootAgentTreeVO;
		
		if (tree != null) {
			for (AgentTreeVO thisSubAgentTreeVO : tree.subAgents) {
				allSubAgentsList.add(thisSubAgentTreeVO.agent);
				
				_getAllSubAgents(allSubAgentsList, thisSubAgentTreeVO);
			}
		}
		return allSubAgentsList;
	}
	
	/**
	 * 
	 * @param allSubAgentsList
	 * @param agentTreeVO
	 * @return
	 */
	public List<SampleVO>  getAllBranchSubAgents(SampleVO root) {
		List<SampleVO> allBranchSubAgentsList = new ArrayList<SampleVO>();
		
		AgentTreeVO tree = getTreeVo(root);
		
		if (tree != null) {
			for (AgentTreeVO thisSubAgentTreeVO : tree.subAgents) {
				
				SampleVO thisAgentVO = thisSubAgentTreeVO.agent;
				if (thisAgentVO.getBranch()) {
					allBranchSubAgentsList.add(thisAgentVO);
				}
				
				_getAllBranchSubAgents(allBranchSubAgentsList, thisSubAgentTreeVO);
			}
		}
		return allBranchSubAgentsList;
	}
	
	/**
	 * 
	 * @param allSubAgentsList
	 * @param agentTreeVO
	 */
	private void _getAllSubAgents(List<SampleVO> allSubAgentsList, AgentTreeVO agentTreeVO) {
		if (agentTreeVO != null) {
			for (AgentTreeVO thisSubAgentTreeVO : agentTreeVO.subAgents) {
				allSubAgentsList.add(thisSubAgentTreeVO.agent);
				
				_getAllSubAgents(allSubAgentsList, thisSubAgentTreeVO);
			}
		}
	}
	
	/**
	 * 
	 * @param allBranchSubAgentsList
	 * @param agentTreeVO
	 */
	private void _getAllBranchSubAgents(List<SampleVO> allBranchSubAgentsList, AgentTreeVO agentTreeVO) {
		if (agentTreeVO != null) {
			for (AgentTreeVO thisSubAgentTreeVO : agentTreeVO.subAgents) {
				
				SampleVO thisAgentVO = thisSubAgentTreeVO.agent;
				if (thisAgentVO.getBranch()) {
					allBranchSubAgentsList.add(thisAgentVO);
				}
				
				_getAllSubAgents(allBranchSubAgentsList, thisSubAgentTreeVO);
			}
		}
	}

	/**
	 * 
	 * @return
	 */
	public List<AgentTreeVO> getSubTrees() {
		return subAgents;
	}

	/**
	 * 
	 * @param of
	 * @param in
	 * @return
	 */
	public static List<SampleVO> getSuccessors(SampleVO of, List<AgentTreeVO> in) {
		for (AgentTreeVO tree : in) {
			if (tree.locateById.containsKey(of)) {
				return tree.getDirectSubAgents(of);
			}
		}
		return new ArrayList<SampleVO>();
	}

	/**
	 * 
	 */
	public String toString() {
		return printTree(0);
	}

	private static final int indent = 2;

	private String printTree(int increment) {
		String s = "";
		String inc = "";

		for (int i = 0; i < increment; ++i) {
			inc = inc + " ";
		}

		s = inc + agent.getName();
		for (AgentTreeVO child : subAgents) {
			s += "\n" + child.printTree(increment + indent);
		}

		return s;
	}
}
