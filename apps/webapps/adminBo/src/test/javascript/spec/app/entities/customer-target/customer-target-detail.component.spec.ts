/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Rx';

import { AdminBoTestModule } from '../../../test.module';
import { CustomerTargetDetailComponent } from '../../../../../../main/webapp/app/entities/customer-target/customer-target-detail.component';
import { CustomerTargetService } from '../../../../../../main/webapp/app/entities/customer-target/customer-target.service';
import { CustomerTarget } from '../../../../../../main/webapp/app/entities/customer-target/customer-target.model';

describe('Component Tests', () => {

    describe('CustomerTarget Management Detail Component', () => {
        let comp: CustomerTargetDetailComponent;
        let fixture: ComponentFixture<CustomerTargetDetailComponent>;
        let service: CustomerTargetService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [AdminBoTestModule],
                declarations: [CustomerTargetDetailComponent],
                providers: [
                    CustomerTargetService
                ]
            })
            .overrideTemplate(CustomerTargetDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(CustomerTargetDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(CustomerTargetService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                spyOn(service, 'find').and.returnValue(Observable.of(new CustomerTarget(123)));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.find).toHaveBeenCalledWith(123);
                expect(comp.customerTarget).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
