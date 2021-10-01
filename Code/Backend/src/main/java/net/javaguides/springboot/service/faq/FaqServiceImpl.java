package net.javaguides.springboot.service.faq;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import net.javaguides.springboot.model.Faq;
import net.javaguides.springboot.repository.FaqRepository;

@Service
public class FaqServiceImpl implements FaqService {

	@Autowired
	private FaqRepository faqRepository;

	public FaqServiceImpl(FaqRepository faqRepository) {
		super();
		this.faqRepository = faqRepository;
	}

	
	/** 
	 * @return List<Faq>
	 */
	@Override
	public List<Faq> getAllFaqs() {

		return faqRepository.findAll();
	}

	
	/** 
	 * @param faq
	 */
	@Override
	public void saveFaq(Faq faq) {
		this.faqRepository.save(faq);

	}

	
	/** 
	 * @param idal
	 * @return Faq
	 */
	@Override
	public Faq getFaqByIdal(int idal) {

		return faqRepository.getOne(idal);
	}

	
	/** 
	 * @param idal
	 */
	@Override
	public void deleteFaqByIdal(int idal) {
		this.faqRepository.deleteById(idal);

	}

}
