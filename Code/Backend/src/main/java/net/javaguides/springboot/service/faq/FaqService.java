package net.javaguides.springboot.service.faq;

import java.util.List;

import net.javaguides.springboot.model.Faq;

public interface FaqService {
	List<Faq> getAllFaqs();

	void saveFaq(Faq faq);

	Faq getFaqByIdal(int idal);

	void deleteFaqByIdal(int idal);

}
