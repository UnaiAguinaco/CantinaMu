package net.javaguides.springboot.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "question")
public class Faq {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public int id_question;

	@Column(name = "creator_id")
	private int creator_id;

	@Column(name = "definition")
	private String definition;

	@Column(name = "answer", columnDefinition = "TEXT")
	private String answer;

	@Column(name = "faq")
	private boolean faq;

	public Faq(int creator_id, String definition, String answer, boolean faq) {
		super();
		this.creator_id = creator_id;
		this.definition = definition;
		this.answer = answer;
		this.faq = faq;
	}

	public Faq() {

	}

	
	/** 
	 * @return int
	 */
	public int getCreatorId() {
		return creator_id;
	}

	
	/** 
	 * @param creator_id
	 */
	public void setIdal(int creator_id) {
		this.creator_id = creator_id;
	}

	
	/** 
	 * @return int
	 */
	public int getQuestionId() {
		return id_question;
	}

	
	/** 
	 * @param id_question
	 */
	public void setQuestionId(int id_question) {
		this.id_question = id_question;
	}

	
	/** 
	 * @return String
	 */
	public String getDefinition() {
		return definition;
	}

	
	/** 
	 * @param definition
	 */
	public void setDefinition(String definition) {
		this.definition = definition;
	}

	
	/** 
	 * @return String
	 */
	public String getAnswer() {
		return answer;
	}

	
	/** 
	 * @param answer
	 */
	public void setAnswer(String answer) {
		this.answer = answer;
	}

	
	/** 
	 * @return boolean
	 */
	public boolean isFaq() {
		return faq;
	}

	
	/** 
	 * @param faq
	 */
	public void setFaq(boolean faq) {
		this.faq = faq;
	}
}
