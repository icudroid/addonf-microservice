/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Rx';
import { Headers } from '@angular/http';

import { AdminBoTestModule } from '../../../test.module';
import { CustomerTargetComponent } from '../../../../../../main/webapp/app/entities/customer-target/customer-target.component';
import { CustomerTargetService } from '../../../../../../main/webapp/app/entities/customer-target/customer-target.service';
import { CustomerTarget } from '../../../../../../main/webapp/app/entities/customer-target/customer-target.model';

describe('Component Tests', () => {

    describe('CustomerTarget Management Component', () => {
        let comp: CustomerTargetComponent;
        let fixture: ComponentFixture<CustomerTargetComponent>;
        let service: CustomerTargetService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [AdminBoTestModule],
                declarations: [CustomerTargetComponent],
                providers: [
                    CustomerTargetService
                ]
            })
            .overrideTemplate(CustomerTargetComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(CustomerTargetComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(CustomerTargetService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN
                const headers = new Headers();
                headers.append('link', 'link;link');
                spyOn(service, 'query').and.returnValue(Observable.of({
                    json: [new CustomerTarget(123)],
                    headers
                }));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.query).toHaveBeenCalled();
                expect(comp.customerTargets[0]).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
