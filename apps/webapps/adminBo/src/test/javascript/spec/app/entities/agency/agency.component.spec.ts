/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Rx';
import { Headers } from '@angular/http';

import { AdminBoTestModule } from '../../../test.module';
import { AgencyComponent } from '../../../../../../main/webapp/app/entities/agency/agency.component';
import { AgencyService } from '../../../../../../main/webapp/app/entities/agency/agency.service';
import { Agency } from '../../../../../../main/webapp/app/entities/agency/agency.model';

describe('Component Tests', () => {

    describe('Agency Management Component', () => {
        let comp: AgencyComponent;
        let fixture: ComponentFixture<AgencyComponent>;
        let service: AgencyService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [AdminBoTestModule],
                declarations: [AgencyComponent],
                providers: [
                    AgencyService
                ]
            })
            .overrideTemplate(AgencyComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(AgencyComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(AgencyService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN
                const headers = new Headers();
                headers.append('link', 'link;link');
                spyOn(service, 'query').and.returnValue(Observable.of({
                    json: [new Agency(123)],
                    headers
                }));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.query).toHaveBeenCalled();
                expect(comp.agencies[0]).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
